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
                        docker=docker.build(image, "--platform linux/amd64 --build-arg --no-cache --pull --force-rm -f Dockerfile .")
                        cleanWs()
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
