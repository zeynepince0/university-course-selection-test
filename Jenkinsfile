pipeline {
    agent any

    tools {
        jdk 'jdk'
        maven 'maven'
    }

    stages {

        stage('1️⃣ Build & Containerize') {
            steps {
                echo '📦 Docker image build ediliyor...'
                sh 'docker-compose build'
            }
        }

        stage('2️⃣ Unit & Integration Tests') {
            steps {
                echo '🧪 Unit + Integration testleri çalışıyor...'
                sh '''
                  mvn test \
                  -Dtest=com.example.course.unit.*,com.example.course.integration.* \
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }

        stage('3️⃣ Deploy App (Docker)') {
            steps {
                echo '🚀 Uygulama container olarak ayağa kalkıyor...'
                sh 'docker-compose up -d'
                sleep 15
            }
        }

        stage('4️⃣ Selenium Tests') {
            agent {
                docker {
                    image 'markhobson/maven-chrome:jdk-21'
                    args '--network course-net'
                }
            }
            steps {
                echo '🌐 Selenium testleri çalışıyor...'
                sh '''
                  mvn test \
                  -Dtest=com.example.course.selenium.* \
                  -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Temizlik yapılıyor...'
            sh 'docker-compose down'
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo '✅ SUCCESS: Tüm testler başarıyla geçti!'
        }
        failure {
            echo '❌ FAILURE: Pipeline hata aldı.'
        }
    }
}
