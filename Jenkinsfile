pipeline {
    agent any

    tools {
        jdk 'jdk'
        maven 'maven'
    }

    stages {

        stage('1️⃣ Build Docker Image') {
            steps {
                echo '📦 Docker image build ediliyor...'
                bat 'docker-compose build'
            }
        }

        stage('2️⃣ Unit & Integration Tests') {
            steps {
                echo '🧪 Unit + Integration testleri çalışıyor...'
                bat '''
                  mvn test ^
                  -Dtest=com.example.course.unit.*,com.example.course.integration.* ^
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }

        stage('3️⃣ Start App Container') {
            steps {
                echo '🚀 Uygulama Docker’da ayağa kalkıyor...'
                bat 'docker-compose up -d'
                sleep(time: 15, unit: 'SECONDS')
            }
        }

        stage('4️⃣ Selenium Tests (Local Chrome)') {
            steps {
                echo '🌐 Selenium testleri çalışıyor (local Chrome)...'
                bat '''
                  mvn test ^
                  -Dtest=com.example.course.selenium.* ^
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Temizlik yapılıyor...'
            bat 'docker-compose down'
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo '✅ SUCCESS: Pipeline tamamen başarılı!'
        }
        failure {
            echo '❌ FAILURE: Pipeline hata aldı.'
        }
    }
}
