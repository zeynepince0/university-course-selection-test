pipeline {
    agent {
        docker {
            image 'markhobson/maven-chrome:jdk-21'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('1- Build') { steps { sh 'mvn clean compile' } }
        stage('2- Test') {
            steps {
                // server.port=0 sayesinde boş port bulur, 8080 ile kavga etmez
                sh 'mvn test -Dserver.port=0'
            }
        }
    }
}