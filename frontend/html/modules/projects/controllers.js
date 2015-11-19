'use strict';

angular.module('Project', ['ngCookies'])
.controller('ProjectController',
    ['$scope', '$rootScope', '$location', '$http', '$cookies',
    function ($scope, $rootScope, $location, $http, $cookies) {
        $http.get('/api/projects').then(function successCallback(response){
            $scope.projects = response.data;
        },
        function errorCallback(response){
            console.error(response);
        });

        // Create user
        $scope.createProject = function(title, description) {
            console.log($scope.loginUser);
            console.log($scope.projects);
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
                $scope.projects.push({
                    "title": title,
                    "description": description,
                    "ownerId": $scope.loginUser.id,
                    "status": 'OPEN'
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Delete user
        $scope.deleteProject = function(project) {
            $http.delete('/api/projects/'+project.id, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                for (var i=0; i < $scope.projects.length; i++){
                    if ($scope.projects[i].id = user.id){
                        delete $scope.projects[i];
                    }
                }
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Change project status
        $scope.changeProjectStatus = function(status) {
            $http.post('/api/projects/change-status', {
                "projectId": $scope.selProject.id,
                "status": status
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.selProject.status = status;
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Create task
        $scope.createTask = function(title, description, projectId) {
            console.log($scope.loginUser);
            console.log($scope.tasks);
            $http.post('/api/tasks', {
                "title": title,
                "description": description,
                "ownerId": $scope.loginUser.id,
                "status": 'OPEN'
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response){
                $scope.tasks.push({
                    "title": title,
                    "description": description,
                    "owner": {"id":$scope.loginUser.id},
                    "status": 'OPEN'
                });
            },
            function errorCallback(response){
                console.error(response);
            });
        }

        // Modal Windows
        $scope.addCreateTaskWindow = function (){
            $('#createTask').modal('show');
        }
        $scope.addCreateProjectWindow = function (){
            $http.get('/api/projects').then(function successCallback(response){
                $scope.projects = response.data;
            },
            function errorCallback(response){
                console.error(response);
            });
            $('#createProject').modal('show');
        }
    }
]);