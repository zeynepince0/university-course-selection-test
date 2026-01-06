pipeline {
    agent {
        docker { image 'markhobson/maven-chrome:jdk-21'; args '-v /root/.m2:/root/.m2' }
    }
    stages {
        stage('1. Kod Çekme & Build') {
            steps { sh 'mvn clean compile'; echo '✅ Build Başarılı!' }
        }
        stage('2. Birim Testleri') {
            steps { sh 'mvn test -Dtest=*unit*'; echo '✅ Birim Testleri Raporlandı!' }
        }
        stage('3. Entegrasyon Testleri') {
            steps { sh 'mvn test -Dtest=*integration*'; echo '✅ Entegrasyon Testleri Raporlandı!' }
        }
        // Hoca her Selenium senaryosu için ayrı stage olabilir dediği için:
        stage('4. Selenium: Ders Seçimi') {
            steps { sh 'mvn test -Dtest=StudentCourseSelectionTest -Dserver.port=0' }
        }
        stage('5. Selenium: Danışman Onayı') {
            steps { sh 'mvn test -Dtest=AdvisorApprovalTest -Dserver.port=0' }
        }
        stage('6. Selenium: Liste Görüntüleme') {
            steps { sh 'mvn test -Dtest=EnrollmentListTest -Dserver.port=0' }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            echo '🏁 Tüm süreç tamamlandı ve raporlar hazır!'
        }
    }
}