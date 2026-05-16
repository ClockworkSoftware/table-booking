plugins {
    kotlin("jvm")
    kotlin("kapt")
	kotlin("plugin.spring") version "2.2.21"
	kotlin("plugin.jpa") version "2.2.21"
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "org.clockwork.table-booking"
version = "$version"

dependencies {
    // SPRING
    implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-h2console")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // PERSISTENCE
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.hibernate.orm:hibernate-processor")
    implementation("jakarta.data:jakarta.data-api:1.0.1")
	implementation("tools.jackson.module:jackson-module-kotlin")
    // NEIGHBORS
    implementation(project(":tb-sdk"))
    // UTILS
    implementation("com.auth0:java-jwt:4.5.2")
    implementation("com.google.guava:guava:33.1.0-jre")
    // LOGGING
    // The Kotlin-friendly facade
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")
    // The actual logging engine
    implementation("org.apache.logging.log4j:log4j-api:2.24.1")
    implementation("org.apache.logging.log4j:log4j-core:2.24.1")
    // KOTLIN
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // OTHER
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	kapt("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")

    // HIBERNATE + JPA
    kapt("org.hibernate.orm:hibernate-processor:7.3.2.Final")
    kapt("org.hibernate.orm:hibernate-jpamodelgen:7.3.2.Final")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
