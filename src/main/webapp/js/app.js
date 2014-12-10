angular.module('employeeApp',['ui.router','ngResource','employeeApp.controllers','employeeApp.services']);

angular.module('employeeApp').config(function($stateProvider,$httpProvider){
    $stateProvider.state('employees',{
        url:'/employees',
        templateUrl:'partials/employees.html',
        controller:'EmployeeListController'
    }).state('viewEmployee',{
       url:'/employees/:id/view',
       templateUrl:'partials/employee-view.html',
       controller:'EmployeeViewController'
    }).state('newEmployee',{
        url:'/employees/new',
        templateUrl:'partials/employee-add.html',
        controller:'EmployeeCreateController'
    }).state('editEmployee',{
        url:'/employees/:id/edit',
        templateUrl:'partials/employee-edit.html',
        controller:'EmployeeEditController'
    });
}).run(function($state){
   $state.go('employees');
});