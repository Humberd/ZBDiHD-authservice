node {
    /* ---TEST--- */
    def testDatabaseUrl = "zbdihd-authservice-test-db"
    def testDatabasePort = 27017
    def testDatabaseName = "admin"
    def testDatabaseUsername = "auth-service"
    def testDatabasePassword = "admin123"
    /* ---DEPLOY--- */
    def deployDatabaseUrl = "zbdihd-authservice-db"
    def deployDatabasePort = 27017
    def deployDatabaseName = "admin"
    def deployDatabaseUsername = "auth-service"
    def deployDatabasePassword = "admin123"


    /**
     * Making sure, that there are only at most 2 artifacts stored on a server,
     * because We don't want to waste a storage on a server, do we?
     */
    //noinspection GroovyAssignabilityCheck
    properties([buildDiscarder(logRotator(
            artifactDaysToKeepStr: '',
            artifactNumToKeepStr: '1',
            daysToKeepStr: '',
            numToKeepStr: '')),
                disableConcurrentBuilds(),
                pipelineTriggers([githubPush()])])

    stage("Pre Cleanup") {
        deleteDir()
    }

    stage("Checkout") {
        git "https://github.com/Humberd/ZBDiHD-authservice.git"
    }

    stage("Build") {
        //noinspection GroovyAssignabilityCheck
        withCredentials([file(credentialsId: 'TC_JWT_PRIVATE_KEY', variable: 'TC_JWT_PRIVATE_KEY'),
                         file(credentialsId: 'TC_JWT_PUBLIC_KEY', variable: 'TC_JWT_PUBLIC_KEY')]) {
            sh "cp \$TC_JWT_PRIVATE_KEY src/main/resources/jwt_private_key.der"
            sh "cp \$TC_JWT_PUBLIC_KEY src/main/resources/jwt_public_key.der"

            withMaven(maven: "Maven") {
                sh "mvn clean install -DskipTests=true"
            }
        }
    }

    stage("Test") {
        //noinspection GroovyAssignabilityCheck
        dockerComposeFile = "production.testing.docker-compose.yml"

        sh "docker-compose -f ${dockerComposeFile} down --rmi all --remove-orphans"
        sh "docker-compose -f ${dockerComposeFile} up -d"

        try {
            withEnv([
                    "COMMIT_HASH=${getCommitHash()}",
                    "BUILD_NO=${getBuildNumber()}",
                    "ZBDIHD_AUTH_TESTS_DATABASE_URL=${testDatabaseUrl}",
                    "ZBDIHD_AUTH_TESTS_DATABASE_PORT=${testDatabasePort}",
                    "ZBDIHD_AUTH_TESTS_DATABASE_NAME=${testDatabaseName}",
                    "ZBDIHD_AUTH_TESTS_DATABASE_USERNAME=${testDatabaseUsername}",
                    "ZBDIHD_AUTH_TESTS_DATABASE_PASSWORD=${testDatabasePassword}",
            ]) {
                withMaven(maven: "Maven") {
                    sh "mvn test -DargLine='-Dspring.profiles.active=production'"
                }
            }
        } finally {
            sh "docker-compose -f ${dockerComposeFile} down --rmi all --remove-orphans"
        }
    }

    stage("Deploy") {
        dockerComposeFile = "production.deploy.docker-compose.yml"

        /**
         * Setting environment variables only for a docker container
         */
        withEnv([
                "COMMIT_HASH=${getCommitHash()}",
                "BUILD_NO=${getBuildNumber()}",
                "ZBDIHD_AUTH_DATABASE_URL=${deployDatabaseUrl}",
                "ZBDIHD_AUTH_DATABASE_PORT=${deployDatabasePort}",
                "ZBDIHD_AUTH_DATABASE_NAME=${deployDatabaseName}",
                "ZBDIHD_AUTH_DATABASE_USERNAME=${deployDatabaseUsername}",
                "ZBDIHD_AUTH_DATABASE_PASSWORD=${deployDatabasePassword}",
        ]) {
            sh "docker-compose -f ${dockerComposeFile} down --rmi all --remove-orphans"
            sh "docker-compose -f ${dockerComposeFile} up -d"
        }
    }

    stage("Post Cleanup") {
        deleteDir()
    }
}

def getCommitHash() {
    return sh(
            script: "git show -s --format=%H",
            returnStdout: true
    ).trim()
}

def getBuildNumber() {
    return env.BUILD_NUMBER
}

def getFileContent(fileName) {
    return sh(
            script: '\$' + fileName,
            returnStdout: true
    )
}