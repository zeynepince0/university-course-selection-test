pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'jdk'
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
                sleep(time: 15, unit: 'SECONDS')
            }
        }

        stage('🧪 Unit Tests') {
            steps {
                bat '''
                  mvn test ^
                  -Dtest=com.example.course.unit.* ^
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }

        stage('🧪 Integration Tests') {
            steps {
                bat '''
                  mvn test ^
                  -Dtest=com.example.course.integration.* ^
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }

        stage('🌐 Selenium Tests') {
            steps {
                bat '''
                  mvn test ^
                  -Dtest=com.example.course.selenium.* ^
                  -Dserver.port=8082 ^
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }
    }

    post {
        always {
            bat 'docker-compose down'
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo '✅ Pipeline tamamen başarılı!'
        }
    }
}
