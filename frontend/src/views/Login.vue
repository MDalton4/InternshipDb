<template>
    <div class="container">
        <b-jumbotron header="Welcome" lead="Sign in with a Merrimack google account to continue">
            <g-signin-button
                :params="params"
                @success="onSuccess"
                @error="onFailure"
                class="btn btn-social btn-google btn-lg"
                >
                <span class="fab fa-google"></span> Sign in with Google
            </g-signin-button>
        </b-jumbotron>
    </div>
</template>

<script>
    import api from '../components/API'

    export default {
        name: "Login",
        methods: {
            onSuccess(googleUser) {
                // The ID token you need to pass to your backend:
                let id_token = googleUser.getAuthResponse().id_token;

                let json = {
                    id_token: id_token
                };

                api.login(json).then(response => {
                    this.$store.dispatch('login', response.data.token).catch(() => {});
                    this.$router.push({path: '/'}).catch(() =>{});
                }).catch((e) => {
                    this.$alertify.error(e.response.data + ' Try again');
                    this.$store.dispatch('logout').catch(() => {});
                });

            },
            onFailure(error) {
                console.log('fail err ' + error);
            },
        },
        data() {
            return {
                params: {
                    client_id: "208869903658-dn8phr16v5tqenf62oo14gvbobmd7vmp.apps.googleusercontent.com"
                }
            }

        },
    }

</script>

<style scoped>
    .container {
        padding-top: 70px;
        height: 100vh;
        background-color: #f2f2f2;
        text-align: center;
    }

    .jumbotron {
        background-color: white;
        box-shadow: 0 -6px 4px #f2f2f2, 0 6px 4px -3px #BFBFBF, 6px 5px 4px -3px #BFBFBF, -6px 5px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    }
    .lead{
        font-size: 1.25em;
    }
    .display-3 {
        font-size: 2em;
    }
    .btn {
        background-color: #DD4B39;
        color: white;
        margin: auto;
        text-align: center;
    }
    /* From bootstrap-social css */
    btn-google-login{color:#fff;background-color:#dd4b39;border-color:rgba(0,0,0,0.2)}.btn-google:focus,.btn-google.focus{color:#fff;background-color:#c23321;border-color:rgba(0,0,0,0.2)}
    .btn-google:hover{color:#fff;background-color:#c23321;border-color:rgba(0,0,0,0.2)}
    .btn-google:active,.btn-google.active,.open>.dropdown-toggle.btn-google{color:#fff;background-color:#c23321;border-color:rgba(0,0,0,0.2)}.btn-google:active:hover,.btn-google.active:hover,.open>.dropdown-toggle.btn-google:hover,.btn-google:active:focus,.btn-google.active:focus,.open>.dropdown-toggle.btn-google:focus,.btn-google:active.focus,.btn-google.active.focus,.open>.dropdown-toggle.btn-google.focus{color:#fff;background-color:#a32b1c;border-color:rgba(0,0,0,0.2)}
    .btn-google:active,.btn-google.active,.open>.dropdown-toggle.btn-google{background-image:none}
    .btn-google.disabled:hover,.btn-google[disabled]:hover,fieldset[disabled] .btn-google:hover,.btn-google.disabled:focus,.btn-google[disabled]:focus,fieldset[disabled] .btn-google:focus,.btn-google.disabled.focus,.btn-google[disabled].focus,fieldset[disabled] .btn-google.focus{background-color:#dd4b39;border-color:rgba(0,0,0,0.2)}
    .btn-google .badge{color:#dd4b39;background-color:#fff}
    .btn-social{position:relative;padding-left:44px;text-align:left;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.btn-social>:first-child{position:absolute;left:0;top:0;bottom:0;width:32px;line-height:34px;font-size:1.6em;text-align:center;border-right:1px solid rgba(0,0,0,0.2)}
    .btn-social.btn-lg{padding-left:61px}.btn-social.btn-lg>:first-child{line-height:45px;width:45px;font-size:1.8em}
</style>
