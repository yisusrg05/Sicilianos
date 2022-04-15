var tinyinit = {
  selector: '#editor',
  language: 'es',
  height: 400,
  menu: {
		//file: {title: 'File', items: 'newdocument'},
		edit: {title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall'},
		insert: {title: 'Insert', items: 'custom_plugin'},
		view: {title: 'View', items: 'visualaid |'},
		format: {title: 'Format', items: 'bold italic underline strikethrough superscript subscript | formats | removeformat'},
		table: {title: 'Table'},
		tools: {title: 'Tools'}
	},
  toolbar: 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media | code custom_plugin',
  plugins: 'link image media code custom_plugin',
  content_css: 'css/styles.css?' + new Date().getTime(),
  style_formats_merge: true,
  style_formats: [{
    title: 'Formats group', items:[{
      title: 'Change circle for greek symbols', selector: 'ul', classes:'greek-symbols',
      },
      {
        title: 'Background gray for elem lists', selector: 'ul', classes:'background-grey',
      },
      {
        title: 'Background gray and greek symbols', selector: 'ul', classes:'greek-symbols background-grey',
      },
    ],
  },
  ],
};

tinymce.addI18n('es_ES',{
  'Formats group': 'Grupo de formatos',
  'Change circle for greek symbols': 'Cambiar circulo por símbolos griegos',
  'Background gray for elem lists': 'Fondo gris para elementos de lista',
  'Background gray and greek symbols': 'Fondo gris y símbolos griegos',
});


tinymce.PluginManager.add('custom_plugin', function(editor) {
  // Toolbar Button
	editor.addButton('custom_plugin', {
		tooltip: 'Custom plugin',
		onclick: showDialog,
		stateSelector: 'cite'
	});

  // Menubar option
	editor.addMenuItem('custom_plugin', {
		text: 'Custom plugin',
		onclick: showDialog,
		context: 'insert',
		prependToContext: true
	});

  function showDialog() {
    var win, data = {}, dom = editor.dom, element = editor.selection.getNode();

    var parent = dom.getParent(editor.selection.getNode(), 'cite');

    if (parent) {
      element = parent;
    }

		if (element.nodeName == 'CITE' && !element.getAttribute('data-mce-object')) {
      var q = new tinymce.dom.DomQuery(element);
      var autor = q.find('span').html();
      var position = q.attr('class');
      var cite = q.text();

      cite = cite.replace(autor, '');

			data = {
				autor: autor,
        cite: cite,
        position: position,
			};
		}
    else {
			element = null;
		}

		function onSubmitForm() {
			var data = win.toJSON();

			data = {
				autor: data.autor,
        cite: data.cite,
        position: data.position,
			};

			editor.undoManager.transact(function() {
				if (!data.cite) {
					if (element) {
						dom.remove(element);
						editor.nodeChanged();
					}

					return;
				}

				if (!element) {
					data.id = '__mcenew';

          var cite = createCite(data);
					editor.selection.setContent(cite.outerHTML);
					element = dom.get('__mcenew');
					dom.setAttrib(element, 'id', null);
				} else {
          var cite = createCite(data);
          dom.remove(element);
					editor.selection.setContent(cite.outerHTML);
				}
			});
		}

    function createCite(data) {
      var cite = document.createElement('cite');
      var autor = document.createElement('span');
      var text_cite = document.createTextNode(data.cite);
      var text_autor = document.createTextNode(data.autor);

      cite.appendChild(text_cite);

      cite.setAttribute('class', data.position);

      if (data.autor != '') {
        autor.appendChild(text_autor);
        autor.setAttribute('class', 'autor');

        cite.appendChild(autor);
      }

      return cite;
    }

		// General settings
		var generalFormItems = [
			{name: 'autor', type: 'textbox', label: 'Autor'},
      {name: 'cite', type: 'textbox', label: 'Cite', multiline: true, rows: 8, size: '300px'},
      {name: 'position', type: 'listbox', label: 'Position',
        values: [
            {text: '', value: ''},
            {text: 'Left', value: 'left'},
            {text: 'Right', value: 'right'}
        ]},
		];

    // Simple default dialog
    win = editor.windowManager.open({
      title: 'Insert/edit cite',
      data: data,
      body: generalFormItems,
      onSubmit: onSubmitForm
    });
  }
});

tinymce.init(tinyinit);