pipeline {
    agent any

    stages {

        stage('🐳 Build Test Image') {
            steps {
                echo 'Docker test image build ediliyor'
                bat 'docker-compose build'
            }
        }

        stage('🧪 Unit Tests') {
            steps {
                echo 'Unit testleri container içinde çalışıyor'
                bat '''
                docker-compose run --rm test-runner ^
                mvn test ^
                -Dtest=com.example.course.unit.*Test ^
                -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('🧪 Integration Tests') {
            steps {
                echo 'Integration testleri container içinde çalışıyor'
                bat '''
                docker-compose run --rm test-runner ^
                mvn test ^
                -Dtest=com.example.course.integration.*Test ^
                -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('🌐 Selenium Tests') {
            steps {
                echo 'Selenium testleri container içinde çalışıyor'
                bat '''
                docker-compose run --rm test-runner ^
                mvn test ^
                -Dtest=com.example.course.selenium.*Test ^
                -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Temizlik'
            bat 'docker-compose down'
        }
        success {
            echo '✅ Tüm testler başarıyla tamamlandı'
        }
        failure {
            echo '❌ Pipeline hata aldı'
        }
    }
}
