def call(String project, String ImageTag, String hubUser){
    
    sh """   
      trivy image --exit-code 1 --severity HIGH,CRITICAL ${hubUser}/${project}:${ImageTag}
      trivy image --format table --output scan.txt ${hubUser}/${project}:${ImageTag}
    """
    archiveArtifacts artifacts: 'scan.txt', fingerprint: true
}

// def call(String aws_account_id, String region, String ecr_repoName){
    
//     sh """
//     trivy image ${aws_account_id}.dkr.ecr.${region}.amazonaws.com/${ecr_repoName}:latest > scan.txt
//     cat scan.txt
//     """
// } trivy image ${hubUser}/${project}:${ImageTag} > scan.txt
// cat scan.txt
