pipeline{
    agent any 
    stages {
        stage('Build and push image to Dockerhub') {
            steps{
                withDockerRegistry(credentialsId:'dockerhub',url:''){
                    sh 'docker build -t phdg/pjsocial:latest .'
                    sh 'docker push phdg/pjsocial:latest'
                }
            }
        }
        stage("Deploy with Ansible"){
            steps{
                sh 'ansible --version'
                sh 'ansible-playbook -i host playbook.yml'
            }
        }
    }
}