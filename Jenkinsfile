pipeline {
    agent {
        docker {
            image 'markhobson/maven-chrome:jdk-21'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('1. Kod Çekme & Build') {
            steps {
                sh 'mvn clean compile'
                echo ' Adım 1 Tamamlandı: Kaynak kodlar başarıyla çekildi ve build edildi.'
            }
        }
        stage('2. Birim Testleri') {
            steps {
                // Sadece unit paketindeki testleri çalıştırır
                sh 'mvn test -Dtest=com.example.course.unit.* -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Adım 2 Tamamlandı: Birim testleri başarıyla sonuçlandı.'
            }
        }
        stage('3. Entegrasyon Testleri') {
            steps {
                // Sadece integration paketindeki testleri çalıştırır
                sh 'mvn test -Dtest=com.example.course.integration.* -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Adım 3 Tamamlandı: Entegrasyon testleri başarıyla sonuçlandı.'
            }
        }
        stage('4. Selenium: Ders Seçimi') {
            steps {
                sh 'mvn test -Dtest=StudentCourseSelectionTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo 'Adım 4 Tamamlandı: Öğrenci ders seçimi senaryosu test edildi.'
            }
        }
        stage('5. Selenium: Danışman Onayı') {
            steps {
                sh 'mvn test -Dtest=AdvisorApprovalTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Adım 5 Tamamlandı: Danışman onay süreci test edildi.'
            }
        }
        stage('6. Selenium: Liste Görüntüleme') {
            steps {
                sh 'mvn test -Dtest=EnrollmentListTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Adım 6 Tamamlandı: Kayıt listeleme senaryosu test edildi.'
            }
        }
    }
    post {
        always {
            // TÜM test raporlarını (Unit + Integration + Selenium) toplu olarak Jenkins arayüzüne aktarır
            junit '**/target/surefire-reports/*.xml'
            echo ' CI/CD Süreci Final Raporu: Tüm testler raporlandı ve Jenkins paneline aktarıldı.'
        }
    }
}