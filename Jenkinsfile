pipeline {
    agent {
        docker {
            image 'markhobson/maven-chrome:jdk-21'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('1- Build Code') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('2- Unit Tests') {
            steps {
                // Sadece Unit testleri çalıştır, Selenium testlerini ayır
                sh 'mvn test -Dtest=*Test,!*IntegrationTest,!*SelectionTest,!*ApprovalTest,!*ListTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('3- Integration Tests') {
            steps {
                sh 'mvn test -Dtest=*IntegrationTest'
            }
        }
        stage('4- System Tests (Selenium)') {
            steps {
                // PORT 8081 üzerinden çalıştırarak çakışmayı önle
                sh 'mvn spring-boot:start -Dspring-boot.run.arguments="--server.port=8081"'
                sh 'mvn test -Dtest=*SelectionTest,*ApprovalTest,*ListTest -Dserver.port=8081'
            }
            post {
                always {
                    sh 'mvn spring-boot:stop'
                }
            }
        }
    }
}