<%--
  Created by IntelliJ IDEA.
  User: a5920
  Date: 2021/1/29
  Time: 上午 11:55
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@latest"></script>
</head>

<body>
<h1>hi</h1>
<div id="editorjs" style="background-color: #b9bbbe"></div>
<button>Save</button>
<script>
    const editor = new EditorJS({
        holder: 'editorjs',

        // tools: {
        //     header: {
        //         class: Header,
        //         inlineToolbar: ['link']
        //     },
        //     list: {
        //         class: List,
        //         inlineToolbar: [
        //             'link',
        //             'bold'
        //         ]
        //     },
        //     embed: {
        //         class: Embed,
        //         inlineToolbar: false,
        //         config: {
        //             services: {
        //                 youtube: true,
        //                 coub: true
        //             }
        //         }
        //     },
        //
        // }
        data: {
            time: 1552744582955,
            blocks: [
                {
                    type: "image",
                    data: {
                        url: "https://cdn.pixabay.com/photo/2017/09/01/21/53/blue-2705642_1280.jpg"
                    }
                }
            ],
            version: "2.11.10"
        }
    })
    let saveBtn = document.querySelector('button')
    saveBtn.addEventListener('click', function () {
        editor.save().then((outputData) => {
            console.log('Save Data: ', outputData)
        }).catch((error) => {
            console.log('Save Failed: ', error)
        })
    })
</script>

</body>
</html>