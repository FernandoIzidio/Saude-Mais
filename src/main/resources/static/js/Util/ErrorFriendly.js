/**
* @param {String} nearElementId
* @param {String} messageError
* @param {String} tagName
 * @param {String} className
 * @return void
* */
export function renderErrorFriendly(nearElementId, messageError, tagName="span", className="alert-error"){

    const tag = document.createElement(tagName);
    tag.className = className;
    tag.textContent = messageError;

    document.getElementById(nearElementId).insertAdjacentElement("afterend", tag);


}