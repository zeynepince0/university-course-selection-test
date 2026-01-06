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
                echo ' Kaynak kodlar başarıyla build edildi.'

        }
        stage('2. Birim Testleri') {
            steps {
                sh 'mvn test -Dtest=com.example.course.unit.* -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Birim testleri tamamlandı ve rapor hazırlandı.'
            }
        }
        stage('3. Entegrasyon Testleri') {
            steps {
                sh 'mvn test -Dtest=com.example.course.integration.* -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Entegrasyon testleri tamamlandı ve rapor hazırlandı.'
            }
        }
        stage('4. Selenium: Ders Seçimi') {
            steps {
                sh 'mvn test -Dtest=StudentCourseSelectionTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Selenium: Öğrenci ders seçimi senaryosu başarıyla test edildi.'
            }
        }
        stage('5. Selenium: Danışman Onayı') {
            steps {
                sh 'mvn test -Dtest=AdvisorApprovalTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Selenium: Danışman onay süreci başarıyla test edildi.'
            }
        }
        stage('6. Selenium: Liste Görüntüleme') {
            steps {
                sh 'mvn test -Dtest=EnrollmentListTest -Dserver.port=0 -Dsurefire.failIfNoSpecifiedTests=false'
                echo ' Selenium: Kayıt listeleme senaryosu başarıyla test edildi.'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            echo ' Tüm CI/CD süreçleri tamamlandı. Raporlar hazır.'
        }
    }
}