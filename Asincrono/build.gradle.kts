plugins {
    id("java")
}

group = "com.asincrono"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    //ASINCRONO
    //Junit
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    //Logger SLF4J
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")


    //Mockito
    testImplementation("org.mockito:mockito-core:3.12.4")




}

tasks.test {
    useJUnitPlatform()
}