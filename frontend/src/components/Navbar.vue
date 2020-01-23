<template>
    <div>
        <b-navbar toggleable="lg" type="dark" class="custom-nav">
            <b-navbar-brand href="#" :to="'/'">Merrimack PIDB</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item href="#" :to="'/'" class="custom-nav-item hvr-underline-from-center">Home</b-nav-item>
                    <b-nav-item href="#" :to="'/search'" class="custom-nav-item hvr-underline-from-center">Search</b-nav-item>
                    <div v-if="isAdmin">
                        <b-nav-item-dropdown text="Admin" class="custom-nav-item hvr-underline-from-center">
                            <b-dropdown-item href="#" :to="'/admin'">Dashboard</b-dropdown-item>
                            <b-dropdown-divider></b-dropdown-divider>
                            <b-dropdown-item href="#" :to="'/admin/add'">Add Internship</b-dropdown-item>
                            <b-dropdown-divider></b-dropdown-divider>
                            <b-dropdown-item href="#" :to="'/admin/analytics'">Analytics</b-dropdown-item>
                            <b-dropdown-item href="#" :to="'/admin/database'">Database</b-dropdown-item>
                        </b-nav-item-dropdown>
                    </div>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown text="Profile" class="custom-nav-item hvr-underline-from-center" right>
                        <template slot="button-content">
                            <i class="fas fa-user-alt fa-lg"></i>
                        </template>
                        <b-dropdown-item href="#" :to="'/profile'">View Profile</b-dropdown-item>
                        <b-dropdown-divider></b-dropdown-divider>
                        <b-dropdown-item href="#" @click="logout">Logout</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>

    export default {
        name: "Navbar",
        data () {
            return {
                searchTerm: '',
            }
        },
        methods: {
          logout() {
              this.$store.dispatch('logout').catch(() => {});
              this.$router.push({path: '/login'}).catch(() => {});
          },
        },
        computed: {
            isAdmin() {
                //return this.$store.getters.isAdmin;
                if(localStorage.getItem("token") !== null) {
                    return JSON.parse(atob(localStorage.getItem("token").split('.')[1]))
                        .roles.some(item => item.authority === "ROLE_ADMIN");
                } else {
                    return false;
                }
            }

        }
    }
</script>

<style scoped>
    .custom-nav{
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        padding-left: 10%;
        padding-right: 10%;
        background-color: #114880;
        -webkit-box-shadow: 0 8px 6px -6px #787878;
        -moz-box-shadow: 0 8px 6px -6px #787878;
        box-shadow: -4px 8px 8px -6px #787878;
        font-weight: 400;
        z-index: 100;
    }
    .custom-nav-item {
        margin: 0 10px;
    }
    ::v-deep .nav-link {
        color: white !important;
    }
    ::v-deep .nav-link:hover {
        color: #FFE000 !important;
    }
    ::v-deep .nav-link.router-link-exact-active{
        color: #FFE000 !important;
    }

    /* Underline From Center */
    .hvr-underline-from-center {
        display: inline-block;
        vertical-align: middle;
        -webkit-transform: perspective(1px) translateZ(0);
        -moz-transform: perspective(1px) translateZ(0);
        -ms-transform: perspective(1px) translateZ(0);
        -o-transform: perspective(1px) translateZ(0);
        transform: perspective(1px) translateZ(0);
        box-shadow: 0 0 1px rgba(0, 0, 0, 0);
        position: relative;
    }
    .hvr-underline-from-center:before {
        content: "";
        position: absolute;
        z-index: -1;
        left: 51%;
        right: 51%;
        bottom: 0;
        background: #FFE000;
        height: 2px;
        -webkit-transition-property: left, right;
        transition-property: left, right;
        -webkit-transition-duration: 0.3s;
        transition-duration: 0.3s;
        -webkit-transition-timing-function: ease-out;
        transition-timing-function: ease-out;
    }
    .hvr-underline-from-center:hover:before, .hvr-underline-from-center:focus:before, .hvr-underline-from-center:active:before {
        left: 0;
        right: 0;
    }

    @media (max-width: 576px) {
        .navbar {
            text-align: left;
        }

    }
</style>
