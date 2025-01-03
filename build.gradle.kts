plugins {
	kotlin("jvm")
	kotlin("kapt")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("com.epages.restdocs-api-spec")
	id("org.asciidoctor.jvm.convert")
}

val asciidoctorExt = "asciidoctorExt"
configurations.create(asciidoctorExt) {
	extendsFrom(configurations.testImplementation.get())
}

val snippetsDir = file("build/generated-snippets") // 스니펫 경로

fun getGitHash(): String {
	return providers.exec {
		commandLine("git", "rev-parse", "--short", "HEAD")
	}.standardOutput.asText.get().trim()
}

group = "${property("projectGroup")}"
version = getGitHash()

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(Integer.valueOf("${property("javaVersion")}"))
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
		jvmToolchain(Integer.valueOf("${property("javaVersion")}"))
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}")
	}
}

dependencies {
	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Spring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

    // DB
	runtimeOnly("com.mysql:mysql-connector-j")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")

	// Test - testcontainers
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")

	// Test - restdocs
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
	testImplementation("io.rest-assured:spring-mock-mvc")
	testImplementation("com.epages:restdocs-api-spec-restassured:${property("restDocsApiSpecVersion")}")
	testImplementation("com.epages:restdocs-api-spec-mockmvc:${property("restDocsApiSpecVersion")}")
}

tasks.withType<Test> {
	outputs.dir(snippetsDir)
	useJUnitPlatform()
	systemProperty("user.timezone", "UTC")
}

tasks.asciidoctor {
	configurations("asciidoctorExt")
	baseDirFollowsSourceFile()
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
	sources {
		include("**/index.adoc") // html로 만들 adoc 파일 설정.
	}
}

tasks.register("copyDocument", Copy::class) {
	dependsOn(tasks.asciidoctor)
	doFirst {
		delete(file("src/main/resources/static/docs"))
	}
	from(file("build/docs/asciidoc"))
	into(file("src/main/resources/static/docs"))
}

tasks.build {
	dependsOn(tasks.getByName("copyDocument"))
}
