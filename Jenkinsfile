pipeline {
    agent {
        docker {
            image 'markhobson/maven-chrome:jdk-21'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('1- Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('2- Test & Report') {
            steps {
                // Dinamik port (0) ile testleri çalıştır
                sh 'mvn test -Dserver.port=0'
            }
            post {
                always {
                    // Test sonuçlarını Jenkins arayüzüne taşır
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}