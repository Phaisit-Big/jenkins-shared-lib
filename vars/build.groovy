def call() {
    pipeline {
        agent none
        stages {
            stage('Prepare') {
                agent { label "node-docker-1"}
                steps{
                    script{
                        cleanWs()
                        print "$WORKSPACE"
                    }
                }
            }
        }
    }
}