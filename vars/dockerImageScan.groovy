def call(String project, String ImageTag, String hubUser){
    // Run the scan but continue to generate reports
    sh "trivy image --exit-code 1 --severity HIGH,CRITICAL ${hubUser}/${project}:${ImageTag} || true"
    
    // Generate human-readable report
    sh "trivy image --format table --output scan.txt ${hubUser}/${project}:${ImageTag}"
    
    // Generate SARIF report for SonarQube
    sh "trivy image --format sarif --output trivy-results.sarif ${hubUser}/${project}:${ImageTag}"
    
    // Archive reports in Jenkins
    archiveArtifacts artifacts: 'scan.txt', fingerprint: true
    archiveArtifacts artifacts: 'trivy-results.sarif', fingerprint: true
    
    // Fail the build if HIGH/CRITICAL vulnerabilities exist
    sh """
       if trivy image --exit-code 1 --severity HIGH,CRITICAL ${hubUser}/${project}:${ImageTag}; then
           echo "No critical vulnerabilities"
       else
           echo "Critical vulnerabilities found!"
           exit 1
       fi
    """
}




// def call(String aws_account_id, String region, String ecr_repoName){
    
//     sh """
//     trivy image ${aws_account_id}.dkr.ecr.${region}.amazonaws.com/${ecr_repoName}:latest > scan.txt
//     cat scan.txt
//     """
// } trivy image ${hubUser}/${project}:${ImageTag} > scan.txt
// cat scan.txt
