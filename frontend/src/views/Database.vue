<template>
    <div class="database-div">

        <div class="container database-container">
            <div class="form-div">
                <h5>Select a Table</h5>
                <b-form-select v-model="tableSelected" :options="tableOptions"></b-form-select>
                <div v-if="tableSelected === 'Tags'" class="tag-btn-container">
                    <b-button size="md" class="mr-1 tag-btn" @click="modalShow = !modalShow">Add Tag</b-button>
                </div>
                <div v-if="tableSelected === 'Internships'" class="tag-btn-container">
                    <b-button size="md" class="mr-1 tag-btn" :to="{name: 'add' }">Add Internship</b-button>
                </div>

                <b-modal v-model="modalShow" @show="resetForm" title="Add a Tag" hide-footer ref="modal">

                    <form ref="addForm" id="tag-form" @submit.prevent="handleSubmit">
                        <b-form-group class="tag">
                            <h5 class="h5-tag">Name</h5> <b-form-input required name="name" v-model="name"></b-form-input>
                            <h5 class="h5-tag">Type</h5> <b-form-select required v-model="type" :options="typeOptions" name="type">

                            <template v-slot:first>
                                <option :value="null" disabled>-- Please select an option --</option>
                            </template>

                        </b-form-select>
                            <b-button class="add-btn" variant="primary" type="submit">Add</b-button>
                        </b-form-group>
                    </form>

                </b-modal>
            </div>
            <div class="table-div">
                <h5>{{tableSelected}} Table:</h5>
                <div v-if="tableSelected === 'Internships'">
                    <b-table
                            :items="internships"
                            :fields="internshipFields"
                            :sort-by.sync="sortBy"
                            :sort-desc.sync="sortDesc"
                            sort-icon-left
                            responsive="sm"
                            striped
                            hover
                            :current-page="internshipsCurrentPage"
                            :per-page="perPage"
                    >
                        <template v-slot:cell(actions_internship)="row">
                            <b-button size="sm" class="mr-1 edit-btn" :to="{name: 'edit', params:{id: row.item.id} }">
                                Edit
                            </b-button>
                            <b-button size="sm" class="mr-1 btn-danger" @click="handleDeleteInternship(row.item.id, row.item)">
                                Delete
                            </b-button>
                        </template>
                    </b-table>

                    <div class="overflow-auto">
                        <b-pagination v-model="internshipsCurrentPage"
                                      :total-rows="internshipTotalRows"
                                      :per-page="perPage"
                                      align="center"
                                      @click.native="scrollToTop">
                        </b-pagination>
                    </div>

                </div>

                <div v-if="tableSelected === 'Users'">
                    <b-table
                            :items="users"
                            :fields="userFields"
                            :sort-by.sync="sortBy"
                            :sort-desc.sync="sortDesc"
                            sort-icon-left
                            responsive="sm"
                            striped
                            hover
                            :current-page="usersCurrentPage"
                            :per-page="perPage"
                    >
                        <template v-slot:cell(actions_user)="row">
                            <div v-if="getEmail !== row.item.email">
                                <div v-if="row.item.role === 'student'">
                                    <b-button size="sm" class="mr-1 promote-btn btn" @click="handlePromote(row.item.id, row.item)">
                                        Promote
                                    </b-button>
                                    <b-button size="sm" class="mr-1 btn-danger" @click="handleDeleteUser(row.item.id, row.item)">
                                        Delete
                                    </b-button>
                                </div>
                                <div v-else-if="row.item.role === 'admin'">
                                    <b-button size="sm" class="mr-1 demote-btn btn" variant="primary" @click="handleDemote(row.item.id, row.item)">
                                        Demote
                                    </b-button>
                                    <b-button size="sm" class="mr-1 btn-danger" @click="handleDeleteUser(row.item.id, row.item)">
                                        Delete
                                    </b-button>
                                </div>
                            </div>
                        </template>
                    </b-table>

                    <div class="overflow-auto">
                        <b-pagination v-model="usersCurrentPage"
                                      :total-rows="usersTotalRows"
                                      :per-page="perPage"
                                      align="center"
                                      @click.native="scrollToTop">
                        </b-pagination>
                    </div>

                </div>

                <div v-if="tableSelected === 'Tags'">
                    <b-table
                            :items="tags"
                            :fields="tagFields"
                            :sort-by.sync="sortBy"
                            :sort-desc.sync="sortDesc"
                            sort-icon-left
                            responsive="sm"
                            striped
                            hover
                            :current-page="tagsCurrentPage"
                            :per-page="perPage"
                    >
                        <template v-slot:cell(actions_tag)="row">
                            <b-button size="sm" class="mr-1 btn-danger" @click="handleDeleteTag(row.item.id, row.item)">
                                Delete
                            </b-button>
                        </template>
                    </b-table>

                    <div class="overflow-auto">
                        <b-pagination v-model="tagsCurrentPage"
                                      :total-rows="tagsTotalRows"
                                      :per-page="perPage"
                                      align="center"
                                      @click.native="scrollToTop">
                        </b-pagination>
                    </div>

                </div>

            </div>
        </div>

    </div>
</template>

<script>
    import api from '../components/API.js'

    export default {
        name: "Database",
        data() {
            return {
                tableSelected: 'Internships',
                tableOptions: [
                    {value: 'Internships', text: 'Internships'},
                    {value: 'Users', text: 'Users'},
                    {value: 'Tags', text: 'Tags'}
                ],
                sortBy: 'id',
                sortDesc: false,
                internshipFields: [
                    { key: 'id', sortable: true },
                    { key: 'position', sortable: true },
                    { key: 'company', sortable: true },
                    { key: 'actions_internship', sortable: false }
                ],
                internships: [],
                userFields: [
                    { key: 'id', sortable: true},
                    { key: 'email', sortable: true},
                    { key: 'role', sortable: true},
                    { key: 'actions_user', sortable: false},
                ],
                users: [],
                tagFields: [
                    {key: 'id', sortable: true},
                    {key: 'name', sortable: true},
                    {key: 'type', sortable: true},
                    {key: 'actions_tag', sortable: false},
                ],
                tags: [],
                modalShow: false,
                typeOptions: [
                    {value: 'Interest', text: 'Interest'},
                    {value: 'Population', text: 'Population'},
                    {value: 'Setting', text: 'Setting'},
                    {value: 'Group', text: 'Group'},
                ],
                name: '',
                type: '',
                internshipsCurrentPage: 1,
                perPage: 20,
                internshipTotalRows: 0,
                usersCurrentPage: 1,
                usersTotalRows: 0,
                tagsCurrentPage: 1,
                tagsTotalRows: 0,
            }
        },
        methods: {
            handleDeleteInternship(id, item) {
                this.$alertify.confirm("Are you sure you want to delete?", ()=> {
                        api.deleteInternship(id).then((response) => {
                            if(response.data === 'deleted') {
                                this.$alertify.success("Internship Deleted");
                                for(let i=0; i<this.internships.length; i++) {
                                    if(this.internships[i].id === item.id){
                                        this.internships.splice(i, 1);
                                    }
                                }
                            }
                        }).catch(() => {});
                    }
                    ,() =>this.$alertify.error("Cancelled")
                );
            },

            handleDeleteUser(id, item) {
                this.$alertify.confirm("Are you sure you want to delete?", ()=> {
                        api.deleteUser(id).then((response) => {
                            if(response.data === 'deleted') {
                                this.$alertify.success("User Deleted");
                                for (let i = 0; i < this.users.length; i++) {
                                    if (this.users[i].id === item.id) {
                                        this.users.splice(i, 1);
                                    }
                                }
                            }
                        }).catch(() => {});
                    }
                    ,() =>this.$alertify.error("Cancelled")
                );
            },

            handlePromote(id, item) {
                api.promoteUser(id).then(response => {
                    if(response.data === 'promoted') {
                        for (let i = 0; i < this.users.length; i++) {
                            if (this.users[i].id === item.id) {
                                this.users[i].role = 'admin';
                            }
                        }
                    }
                }).catch(() => {});
            },

            handleDemote(id, item) {
                api.demoteUser(id).then(response => {
                    if(response.data === 'demoted') {
                        for (let i = 0; i < this.users.length; i++) {
                            if (this.users[i].id === item.id) {
                                this.users[i].role = 'student';
                            }
                        }
                    }
                }).catch(() => {});
            },

            handleDeleteTag(id, item) {
                this.$alertify.confirm("Are you sure you want to delete?", ()=> {
                        api.deleteTag(id).then((response) => {
                            if(response.data === 'deleted') {
                                this.$alertify.success("Tag Deleted");
                                for (let i = 0; i < this.tags.length; i++) {
                                    if (this.tags[i].id === item.id) {
                                        this.tags.splice(i, 1);
                                    }
                                }
                            }
                        }).catch(() => {});
                    }
                    ,() =>this.$alertify.error("Cancelled")
                );
            },

            handleSubmit(){

                let json = {
                    "name": this.name,
                    "type": this.type
                };

                api.addTag(json).then((response) => {
                    if(response.data === 'added') {
                        this.tags.push({id: 'REFRESH', name: this.name, type: this.type});
                        this.modalShow = false;
                    }
                })
                .catch(() => {});
            },

            resetForm(){
                this.name = null;
                this.type = null;
            },

            getInternships(){
                api.getAllInternships().then((response) => {
                    for(let i=0; i<response.data.length; i++) {
                        let curData = response.data[i];
                        this.internships.push({id: curData.id, position: curData.position, company: curData.company});
                    }
                    this.internshipTotalRows = this.internships.length;
                }).catch(() => {});
            },

            getTags() {
                api.getAllTags().then((response) => {
                    for(let i=0; i<response.data.length; i++) {
                        let curData = response.data[i];
                        this.tags.push({id: curData.id, name: curData.name, type: curData.type});
                    }
                    this.tagsTotalRows = this.tags.length;
                }).catch(() => {});
            },

            getUsers() {
                api.getAllUsers().then((response) => {
                    for(let i=0; i<response.data.length; i++) {
                        let curData = response.data[i];
                        let roles = response.data[i].authorities;
                        let curRoles = [];
                        for(let i=0; i<roles.length; i++) {
                            curRoles.push(roles[i].authority);
                        }
                        if(curRoles.includes("ROLE_ADMIN")) {
                            this.users.push({id: curData.id, email: curData.username, role: 'admin'});
                        } else {
                            this.users.push({id: curData.id, email: curData.username, role: 'student'});
                        }

                    }
                    this.usersTotalRows = this.users.length;
                }).catch(() => {});
            },
            scrollToTop() {
                window.scrollTo({
                    top: 0,
                    left: 0,
                    behavior: 'smooth'
                });
            },
        },
        created() {
            this.getInternships();
            this.getTags();
            this.getUsers();
        },
        computed: {
            getEmail() {
                return JSON.parse(atob(localStorage.getItem("token").split('.')[1])).sub;
            }
        }
    }
</script>

<style scoped>
    .database-div{
        margin-top: 15px;
        display: flex;
        flex-direction: column;
    }
    .database-container{
        background-color: white;
        border-radius: 10px;
        border: 1px solid rgba(132,132,132,.25);
        box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -webkit-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        -moz-box-shadow: 0 -4px 4px #f2f2f2, 0 4px 4px -3px #BFBFBF, 4px 2px 4px -3px #BFBFBF, -4px 2px 4px -3px #bfbfbf;
        padding: 20px;
        text-align: left;
    }
    .edit-btn, .tag-btn{
        background-color: #1761AD;
    }
    .edit-btn:active, .tag-btn:active{
        background-color: #114880!important;
    }
    .h5-tag{
        margin-top: 10px;
    }

    .promote-btn{
        background-color: #1761AD;
    }
    .promote-btn:hover{
        background-color: #114880;
    }

    .add-btn{
        margin-top: 20px;
        padding: 10px 20px;
        float: right;
    }

    .form-div{
        padding: 10px;
        margin-bottom: 20px;
    }
    .tag-btn-container{
        margin-top: 20px;
    }
</style>
