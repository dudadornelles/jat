node() {
    withEnv(["GLOBAL_ENV_VAR=true"]) {
        stage('first') {
            sh './runscript'
            withEnv(['LOCAL_VAR=1']) {
                sh './runanotherscript'
            }
            withEnv(['LOCAL_VAR=2']) {
                sh './yetanotherone'
            }

        }
    }

}