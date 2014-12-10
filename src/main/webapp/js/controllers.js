angular.module('employeeApp.controllers',[]).controller('EmployeeListController',function($scope,$state,popupService,$window,Employee){

    $scope.employees=Employee.query();

    $scope.deleteEmployee=function(employee){
        if(popupService.showPopup('Really delete this?')){
            employee.$delete(function(){
                $window.location.href='/index.html';
            });
        }
    }

}).controller('EmployeeViewController',function($scope,$stateParams,Employee){

    $scope.employee=Employee.get({id:$stateParams.id}, function(){
        //After Load
    });


}).controller('EmployeeCreateController',function($scope,$state,$stateParams,Employee){

    $scope.employee=new Employee();

    $scope.addEmployee=function(){
        $scope.employee.$save(function(){
            $state.go('employees');
        });
    }

}).controller('EmployeeEditController',function($scope,$state,$stateParams,Employee){

    $scope.updateEmployee=function(){
        $scope.employee.$update(function(){
            $state.go('employees');
        });
    };

    $scope.loadEmployee=function(){
        $scope.employee=Employee.get({id:$stateParams.id}, function(){
            //after Load
        });

    };

    $scope.loadEmployee();
});