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
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.kafka:spring-kafka")

	// Redisson
	implementation("org.redisson:redisson-spring-boot-starter:${property("redissonVersion")}")

    // DB
	runtimeOnly("com.mysql:mysql-connector-j")

	// Prometheus
	implementation("io.micrometer:micrometer-registry-prometheus")

	// TSID
	implementation("com.github.f4b6a3:tsid-creator:${property("tsidCreatorVersion")}")

	// Feign
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	// Swagger UI
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocOpenApiVersion")}")
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
	testImplementation("org.testcontainers:kafka")

	// Test - restdocs
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
	testImplementation("io.rest-assured:spring-mock-mvc")
	testImplementation("com.epages:restdocs-api-spec-restassured:${property("restDocsApiSpecVersion")}")
	testImplementation("com.epages:restdocs-api-spec-mockmvc:${property("restDocsApiSpecVersion")}")
}

// OpenAPI Specification
configure<com.epages.restdocs.apispec.gradle.OpenApi3Extension> {
	setServer("http://localhost:8080")
	title = "콘서트 예약 서비스 API"
	description = "콘서트 예약 서비스 API 명세서"
	version = getGitHash()
	format = "json"
	outputDirectory = "build/resources/main/static"
}

tasks.withType<Test> {
	useJUnitPlatform()
	systemProperty("user.timezone", "UTC")
}

tasks.bootJar {
	dependsOn(":openapi3")
	archiveFileName = "app.jar"
}

tasks.register<Copy>("copyPrivate") {
	from("concert-config") {
		include("*.yml")
	}
	into("src/main/resources")
}

tasks.named("processResources") {
	dependsOn("copyPrivate")
}

tasks.withType<JavaCompile> {
	options.compilerArgs.add("-parameters")
}


