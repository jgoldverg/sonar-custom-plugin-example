window.registerExtension('overops/custom_page_global', function (options) {
  options.el.textContent = 'Global Page!';
  return function () {
    options.el.textContent = '';
  };
});