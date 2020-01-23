<template>
  <div class="home-div">

    <div class="container container-top">
      <h2>Welcome</h2>
        <div>
          <b-row>
            <b-col><b-link :to="'/search'">Search for Internships »</b-link></b-col>
            <b-col><b-link :to="'/profile'">Edit Profile »</b-link></b-col>
          </b-row>
        </div>
    </div>

    <div class="container container-bottom">

      <div class="recently-added">
        <h5>Recently Added</h5>
          <div v-if="!loading">
            <b-row>
              <b-col sm="4">
                <InternshipCard v-if="internships.length === 1 || 2 || 3 || 4 || 5 || 6" :internship="internships[0]"/>
              </b-col>
              <b-col sm="4">
                <InternshipCard  v-if="internships.length === 2 || 3 || 4 || 5 || 6" :internship="internships[1]"/>
              </b-col>
              <b-col sm="4">
                <InternshipCard  v-if="internships.length === 3 || 4 || 5 || 6" :internship="internships[2]"/>
              </b-col>
            </b-row>

            <b-row>
              <b-col sm="4">
                <InternshipCard  v-if="internships.length === 4 || 5 || 6" :internship="internships[3]"/>
              </b-col>
              <b-col sm="4">
                <InternshipCard  v-if="internships.length === 5 || 6" :internship="internships[4]"/>
              </b-col>
              <b-col sm="4">
                <InternshipCard  v-if="internships.length === 6" :internship="internships[5]"/>
              </b-col>
            </b-row>
          </div>
        <div v-if="loading">
          <div class="text-center"><b-spinner variant="primary" label="Spinning"></b-spinner></div>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
    import InternshipCard from '../components/InternshipCard.vue';
    import api from '../components/API';

    export default {
      name: 'home',
      components: {
        InternshipCard
      },
      data() {
          return{
              internships: [],
              loading: false,
          }
      },
      methods: {
          getRecent() {
              this.loading = true;
              api.getMostRecent().then(res => {
                  this.internships = res.data;
                  this.loading = false;
              }).catch(() => {});
          },
      },
      created() {
          this.getRecent();
      }
    }

</script>

<style scoped>
  .home-div {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    padding-bottom: 20px;
  }
  .container{
    background-color: white;
    border: 1px solid rgba(132,132,132,.25);
  }
  .container-top{
    margin-bottom: 20px;
    border-radius: 10px;
    box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    text-align: left;
    padding: 25px 25px;
  }
  .container-bottom{
    border-radius: 10px;
    box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
    padding: 20px 20px 50px 20px;
  }
  a{
    padding-top: 2%;
    font-size: 1em;
    color: #1761AD;
  }
  .recently-added, .popular{
    text-align: left;
  }
  .recently-added{
    padding: 10px 0;
  }
  .popular{
    padding: 30px 0;
  }
  h5{
    padding: 10px 0;
  }
  h2{
    padding: 10px 0 20px 0;
  }
  .card-deck{
    padding: 20px 10px;
  }

  .col-sm-4{
    padding: 20px 10px;
  }

</style>
