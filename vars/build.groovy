def call(image,port) {
   pipeline {
    agent { label "dev1"}
    parameters {
        string(name: 'VERSION', trim: true)
    }
    stages {
        stage('Build & Push') {
            steps{
                script{
                    withDockerRegistry(credentialsId: "dockerhub") {
                        docker=docker.build(image, "--platform linux/amd64 --build-arg --no-cache --pull --force-rm -f Dockerfile .")
                        docker.push(params.VERSION)
                        cleanWs()
                    }
                }
            }
        }
        stage('Deploy') {
            steps{
                script{
                    sh "docker run -d -e PORT=${port} -p 7123:${port} cmtttbrother/66k-rtn-intelligence:" + params.VERSION
                    cleanWs()
                }
            }
        }
    }
}
}
