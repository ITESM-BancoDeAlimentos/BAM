function fixHistory(title, url) {
    if (!!history && !!history.pushState) {
        history.replaceState({}, title, url);
    }
}