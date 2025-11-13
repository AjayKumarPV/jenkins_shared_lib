def call(credentialsId){

    withSonarQubeEnv(credentialsId: credentialsId) {
         sh """
            mvn clean package sonar:sonar \
              -Dsonar.dependencyCheck.reportPath=trivy-results.sarif
         """
    }
}
