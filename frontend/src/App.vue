<template>
  <div id="app">
    <Navbar v-if="this.$route.path !=='/login'"></Navbar>
    <keep-alive include="Search">
      <router-view/>
    </keep-alive>
  </div>
</template>

<script>
  import Navbar from './components/Navbar'

  export default {
    name: 'app',
    components: {
      Navbar
    },
    watch: {
      $route(to, from) {
        document.title = to.meta.title;
        from;
      }
    },
  }
</script>

<style>
  html, body{
    height:100vh;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    background-color: #f2f2f2;
    min-height: 100%;
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }
</style>
