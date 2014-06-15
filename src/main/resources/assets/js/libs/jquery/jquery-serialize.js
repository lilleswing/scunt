/*
 * Saving this code, not sure where it should go
jQuery.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
            options.url = 'http://localhost:8080/api' + options.url;
        });
*/

jQuery.fn.serializeObject = function(){
  var arrayData, objectData;
  arrayData = this.serializeArray();
  objectData = {};

  jQuery.each(arrayData, function() {
    var value;

    if (this.value != null) {
      value = this.value;
    } else {
      value = '';
    }

    if (objectData[this.name] != null) {
      if (!objectData[this.name].push) {
        objectData[this.name] = [objectData[this.name]];
      }

      objectData[this.name].push(value);
    } else {
      objectData[this.name] = value;
    }
  });

  return objectData;
}
