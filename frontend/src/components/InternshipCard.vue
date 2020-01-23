<template>
    <b-card no-body>
        <b-card-body>
            <h5><b-link :to="{name: 'internship', params:{id: internship.id} }">{{internship.position}}</b-link></h5>
            <p>{{internship.company}}</p>
            <div class="description-div">
                {{internship.description}}
            </div>
        </b-card-body>
    </b-card>
</template>

<script>
    export default {
        name: "InternshipCard",
        props: ['internship'],
    }
</script>

<style scoped>
    .card{
        background-color: #f5f5f5;
        box-shadow: 1px 4px 4px -3px #bfbfbf;
        padding: 5px;
    }
    h4.card-title{
        font-size: 1.25em!important;
    }
    a{
        color: #1761AD;
    }
    a:hover{
        color: #114880;
        text-decoration: underline;
    }

    .description-div{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
    }
</style>
