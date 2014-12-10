angular.module('employeeApp.services',[]).factory('Employee',function($resource){
    return $resource('http://localhost:8080/api/employees/:id',{id:'@id'},{
        update: {
            method: 'PUT'
        }
    });
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});