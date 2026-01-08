pipeline {
    agent any

    tools {
        jdk 'jdk'
        maven 'maven'
    }

    stages {

        stage('📦 Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('🐳 Start Backend Container') {
            steps {
                bat 'docker-compose up -d --build'
                sleep 15
            }
        }

        stage('🧪 Unit Tests') {
            steps {
                bat 'mvn test -Dtest=*Test'
            }
        }

        stage('🧪 Integration Tests') {
            steps {
                bat 'mvn test -Dtest=*IT'
            }
        }

        stage('🌐 Selenium Tests') {
            steps {
                bat 'mvn test -Dtest=*E2E -Dserver.port=8082'
            }
        }
    }

    post {
        always {
            bat 'docker-compose down'
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo '✅ Pipeline SUCCESS'
        }
        failure {
            echo '❌ Pipeline FAILED'
        }
    }
}
