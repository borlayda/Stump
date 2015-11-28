(function () {
    var multipickerModule = angular.module('multipicker', []);

    multipickerModule.directive('multipicker', [function () {
        return {
            restrict: 'E',
            templateUrl: 'directives/multipicker/multipicker.html',
            scope: {
                selectedItems: "=",
                notSelectedItems : "="
            },
            link: function (scope, element, attrs) {
                if (attrs.displayAttr) {
                    scope.displayAttr = attrs.displayAttr;
                }
                scope.removeItem = function (item) {
                    scope.selectedItems.splice(scope.selectedItems.indexOf(item), 1);
                    if (scope.notSelectedItems.indexOf(item) == -1) {
                        scope.notSelectedItems.push(item);
                    }
                };
                scope.addItem = function (item) {
                    scope.notSelectedItems.splice(scope.notSelectedItems.indexOf(item), 1);
                    if (scope.selectedItems.indexOf(item) == -1) {
                        scope.selectedItems.push(item);
                    }
                };
            }
        }

    }]);

})();