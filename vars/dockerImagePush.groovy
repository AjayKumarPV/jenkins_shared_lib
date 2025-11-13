def call(String project, String ImageTag, String hubUser){
    script {
        // Abort push if Trivy scan found critical vulnerabilities
        if (currentBuild.result == 'UNSTABLE') {
            error("❌ Critical vulnerabilities detected in Trivy scan. Aborting Docker push.")
        }

        // Docker login using credentials
        withCredentials([usernamePassword(
                credentialsId: "docker",
                usernameVariable: "USER",
                passwordVariable: "PASS"
        )]) {
            sh "docker login -u '$USER' -p '$PASS'"
        }

        // Push image
        sh "docker image push ${hubUser}/${project}:${ImageTag}"
    }
}



// def call(String aws_account_id, String region, String ecr_repoName){
    
//     sh """
//      aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${aws_account_id}.dkr.ecr.${region}.amazonaws.com
//      docker push ${aws_account_id}.dkr.ecr.${region}.amazonaws.com/${ecr_repoName}:latest
//     """
// }
