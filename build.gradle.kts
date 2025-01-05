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

val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { file("build/generated-snippets") }

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

// Production Dependencies
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

	// TSID
	implementation("com.github.f4b6a3:tsid-creator:5.2.6")
}

// Test Dependencies
dependencies {
	// Spring Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")

	// Test - testcontainers
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")

	// Test - restdocs
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
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
	dependsOn(tasks.test)
	configurations(asciidoctorExt.name)
	baseDirFollowsSourceFile()
	inputs.dir(snippetsDir)
	outputs.file("build/docs/asciidoc")

	sources {
		include("**/index.adoc") // html로 만들 adoc 파일 설정.
	}
}

tasks.resolveMainClassName {
	dependsOn(tasks.getByName("copyDocument"))
}

tasks.register("copyDocument", Copy::class) {
	dependsOn(tasks.asciidoctor)
	from(file("build/docs/asciidoc"))
	into(file("build/resources/main/static/docs"))

	include("index.html")
	doFirst {
		delete(file("src/main/resources/static/docs"))
	}
}

tasks.build {
	dependsOn(tasks.getByName("copyDocument"))
}

tasks.bootJar {
	dependsOn(tasks.getByName("copyDocument"))
}
