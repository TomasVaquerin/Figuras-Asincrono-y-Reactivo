plugins {
    id("java")
}

group = "com.asincrono"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    // Project Reactor
    implementation("io.projectreactor:reactor-core:3.6.10")

    //Mockito
    testImplementation("org.mockito:mockito-core:3.12.4")

    // JUnit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    //R2DBC
    implementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
    implementation ("io.r2dbc:r2dbc-spi:0.9.1.RELEASE")
    //R2DBC pool de conexiones
    implementation("io.r2dbc:r2dbc-pool:0.8.5.RELEASE")

}

tasks.test {
    useJUnitPlatform()
}