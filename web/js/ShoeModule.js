class ShoeModule {
    createModel() {
        const modelName = document.getElementById('model-name').value;
        const modelFirm = document.getElementById('model-firm').value;
        const modelSize = document.getElementById('model-size').value;
        const modelPrice = document.getElementById('model-price').value;
        const modelAmount = document.getElementById('model-amount').value;

        const newModel = {
            "modelName": modelName,
            "modelFirm": modelFirm,
            "modelSize": modelSize,
            "modelPrice": modelPrice,
            "modelAmount": modelAmount,
        };

        let newModelPromise = fetch('createModel', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            credentials: 'include',
            body: JSON.stringify(newModel)
        });
        newModelPromise.then(response => response.json())
            .then(response => {
                document.getElementById('info').innerHTML = response.info;
                // sessionStorage.setItem('newModel', JSON.stringify(newModel));
                const body = document.getElementsByTagName('body');
                    body[0].style.transition = 'ease all 0.4s';
                    body[0].style.transitionTimingFunction = 'cubic-bezier(.76,.08,.47,.79)';
                    body[0].style.backgroundColor = 'rgb(0, 255, 0)'
                    setTimeout(() => {
                        body[0].style.transition = 'ease all 0.7s';
                        body[0].style.backgroundColor = 'white'
                    }, 230);
            })
            .catch(error => {
                const body = document.getElementsByTagName('body');
                body[0].style.transition = 'ease all 0.4s';
                body[0].style.transitionTimingFunction = 'cubic-bezier(.76,.08,.47,.79)';
                body[0].style.backgroundColor = 'red'
                setTimeout(() => {
                    body[0].style.transition = 'ease all 0.7s';
                    body[0].style.backgroundColor = 'white'
                }, 230);
            });
    }
    getListModels() {
        let promiseListModels = fetch('getListModels', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
        });
        promiseListModels.then(response => response.json())
                .then(response => {
                    if(response.status) {
                        let modelSelect = document.getElementById('list-models');
                        modelSelect.options.length = 0;
                        let option = null;
                        option = document.createElement('option');
                        option.text = "-Выберите модель-";
                        option.value = '';
                        modelSelect.add(option);
                        for (let i = 0; i < response.options.length; i++) {
                            option = document.createElement('option');
                            option.text = response.options[i].modelName + ' // ' + response.options[i].modelFirm + ' // ' + response.options[i].modelPrice + '$';
                            option.value = response.options[i].id;
                            modelSelect.add(option);
                        }
                    }else {
                        let modelSelect = document.getElementById('list-models');
                        modelSelect.options.length = 0;
                        let option = null;
                        option = document.createElement('option');
                        option.text = "Список моделей пуст..."
                        option.value = '';
                        document.getElementById('info').innerHTML = response.info;
                    }
                })
                .catch(error => {
                    document.getElementById('info').innerHTML = "insertListModels" + error.info;
                });
    }
    insertModelInfo() {
        const modelId = document.getElementById('list-models').value;
        const model = {
            "id": modelId
        }
        let promiseGetModel = fetch('getModel', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset:utf8'
            },
            credentials: 'include',
            body: JSON.stringify(model)
        });
        promiseGetModel.then(response => response.json())
                .then(response => {
                    if(response.status) {
                        document.getElementById('model-name').value = response.model.modelName;
                        document.getElementById('model-firm').value = response.model.modelFirm;
                        document.getElementById('model-size').value = response.model.modelSize;
                        document.getElementById('model-price').value = response.model.modelPrice;
                        document.getElementById('model-amount').value = response.model.modelAmount;
                    }else {
                        document.getElementById('info').innerHTML = response.info;                 
                    }
                })
                .catch(error => {
                    document.getElementById('info').innerHTML = "insertModelInfo " + error.info;
                });

    }
    editModel() {
        const modelId = document.getElementById('list-models').value;
        const modelName = document.getElementById('model-name').value;
        const modelFirm = document.getElementById('model-firm').value;
        const modelSize = document.getElementById('model-size').value;
        const modelPrice = document.getElementById('model-price').value;
        const modelAmount = document.getElementById('model-amount').value;
        const editModel = {
            "id": modelId,
            "modelName": modelName,
            "modelFirm": modelFirm,
            "modelSize": modelSize,
            "modelPrice": modelPrice,
            "modelAmount": modelAmount,
        };

        let promiseEditModel = fetch('editModel', {
            method: 'POST',
            headers: {
                 'Content-Type': 'application/json;charset:utf8'
            },
            credentials: 'include',
            body: JSON.stringify(editModel)
        });
        promiseEditModel.then(response => response.json())
            .then(response => {
                if(response.status) {
                    const body = document.getElementsByTagName('body');
                    body[0].style.transition = 'ease all 0.4s';
                    body[0].style.transitionTimingFunction = 'cubic-bezier(.76,.08,.47,.79)';
                    body[0].style.backgroundColor = 'rgb(0, 255, 0)'
                    setTimeout(() => {
                        body[0].style.transition = 'ease all 0.7s';
                        body[0].style.backgroundColor = 'white'
                    }, 230);
                    document.getElementById('info').innerHTML = response.info;
                }else {
                    document.getElementById('info').innerHTML = response.info;
                }
            })
            .catch(error => {
                document.getElementById('info').innerHTML = error.info;
                const body = document.getElementsByTagName('body');
                body[0].style.transition = 'ease all 0.4s';
                body[0].style.transitionTimingFunction = 'cubic-bezier(.76,.08,.47,.79)';
                body[0].style.backgroundColor = 'red'
                setTimeout(() => {
                    body[0].style.transition = 'ease all 0.7s';
                    body[0].style.backgroundColor = 'white'
                }, 230);
            });
    }
}
const shoeModule = new ShoeModule();
export{shoeModule};