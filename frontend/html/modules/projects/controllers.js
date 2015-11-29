'use strict';

angular.module('Project', ['ngCookies'])
.controller('ProjectController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $scope.TASK_TYPES = ["ISSUE", "TASK"];
        $scope.PROJECT_STATUSES = ["OPEN", "IN_PROGRESS", "RESOLVED", "REOPENED", "CLOSED"];

        $http.get('/api/projects').then(function successCallback(response){
            $scope.projects = response.data;
        },
        function errorCallback(response){
            alert(response.data.message);
        });

        // Create user
        $scope.createProject = function(title, description) {
            $http.post('/api/projects', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "status": 'OPEN'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                    $http.get('/api/projects').then(function successCallback(response){
                            setTimeout(function () {
                                $scope.projects = response.data;
                                $scope.$apply();
                            }, 0);
                        },
                        function errorCallback(response){
                            alert(response.data.message);
                        });
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new project!\n"+response.data);
                alert(response.data.message);
            });
        };

        // Delete user
        $scope.deleteProject = function(project) {
            $http.delete('/api/projects/'+project.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $http.get('/api/projects').then(function successCallback(response){
                    setTimeout(function () {
                        $scope.projects = response.data;
                        $scope.$apply();
                    }, 0);
                },
                function errorCallback(response){
                    alert(response.data.message);
                });
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        }

        // Change project status
        $scope.changeProjectStatus = function(status) {
            console.log($scope.selProject.id + " " + status);
            $http.post('/api/projects/change-status', {
                "id": $scope.selProject.id,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        }

        // Change Project's data
        $scope.changeProject = function(projectId, title, description, ownerId, status, selectedUsers) {
            $http.post('/api/projects/change', {
                "projectId": projectId,
                "title": title,
                "description": description,
                "ownerId": ownerId,
                "status": status,
                "users": selectedUsers.map(function (u) {
                    return u.id;
                })
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
            },
            function errorCallback(response){
                alert(response.data.message);
            });
        };

        // Create task
        $scope.createTask = function(title, description, projectId, type) {
            console.log($scope.loginUser);
            console.log($scope.tasks);
            $http.post('/api/tasks', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "projectId": projectId,
                "status": 'OPEN',
                "type": type
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.getProject($scope.selProject);
                $scope.title = "";
                $scope.description = "";
            },
            function errorCallback(response){
                alert("Can't add new task!\n"+response.data);
                alert(response.data.message);
            });
        };

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $scope.showError("hmmm");
            $('#createTask').modal('show');
        };
        $scope.addCreateProjectWindow = function (){
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                alert(response.data.message);
            });
            $('#createProject').modal('show');
        };
        $scope.addChangeProjectWindow = function (){
            $('#changeProject').modal('show');
            $scope.usersNotInProject = $scope.users.filter(function (val) {
                return $scope.selProject.users.map(function (it) {return it.id}).indexOf(val.id) == -1;
            });
        }
    }
]);