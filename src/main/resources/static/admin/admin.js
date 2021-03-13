angular.module('app').controller('adminController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                alert('Добавлен новый продукт');
            });
    };

    $scope.getCategoriesList = function () {
        $http({
            url: contextPath + '/api/v1/categories',
            method: 'GET'
        })
            .then(function (response) {
                $scope.CategoriesList = response.data;
            });
    };

    $scope.getCategoriesList();
});