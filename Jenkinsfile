pipeline {
    agent any

    tools {
        jdk 'jdk'    // Jenkins Global Tool Configuration ismin
        maven 'maven' // Jenkins Global Tool Configuration ismin
    }

    stages {
        stage('1. Build & Containerize') {
            steps {
                echo '📦 Docker imajı build ediliyor...'
                sh 'docker-compose build'
            }
        }

        stage('2. Unit & Integration Tests') {
            steps {
                echo '🧪 İç testler (Unit/Integration) koşuyor...'
                // Bunları doğrudan Jenkins üzerindeki Maven ile yapabiliriz
                sh 'mvn test -Dtest=com.example.course.unit.*,com.example.course.integration.* -Dsurefire.failIfNoSpecifiedTests=false'
            }
        }

        stage('3. Deploy App (Container)') {
            steps {
                echo '🚀 Uygulama test için başlatılıyor...'
                sh 'docker-compose up -d'
                // Uygulamanın tamamen hazır olması için bekle
                sleep 15
            }
        }

        stage('4. Selenium Tests (Inside Docker Container)') {
            agent {
                docker {
                    image 'markhobson/maven-chrome:jdk-21'
                    args '--network host' // Hosttaki konteynera erişebilmek için
                }
            }
            steps {
                echo '🌐 Selenium senaryoları Docker içinden koşuyor...'
                sh 'mvn test -Dtest=com.example.course.selenium.* -Dserver.port=8082 -Dsurefire.failIfNoSpecifiedTests=false'
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
            echo '✅ SUCCESS: Tüm testler konteyner ortamında başarıyla tamamlandı!'
        }
    }
}