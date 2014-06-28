( function() {
    var app = angular.module('scunt', ['directive.g+signin']);
    app.controller("ScuntController", function() {
        this.settings = {
            tab: "home",
            loggedIn: false
        };
        this.setTab = function(newTab) {
            this.settings.tab = newTab;
        };
        this.isTab = function(newTab) {
            return this.settings.tab === newTab;
        };
        this.isLoggedIn = function() {
            return this.settings.loggedIn;
        };
    });

    app.directive("menuNavbar", function() {
        return {
            restrict: 'E',
            templateUrl: "templates/menu/menuTemplate.html"
        };
    });
})();
