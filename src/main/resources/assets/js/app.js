( function() {
    var app = angular.module('scunt', []);
    app.controller("ScuntController", function() {
        this.settings = {
            tab: "home"
        };
        this.setTab = function(newTab) {
            this.settings.tab = newTab;
        }
        this.isTab = function(newTab) {
            return this.settings.tab === newTab;
        }
    });

    app.directive("menuNavbar", function() {
        return {
            restrict: 'E',
            templateUrl: "templates/menu/menuTemplate.html"
        };
    });
})();
