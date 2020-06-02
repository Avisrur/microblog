package ct.microblog.support

import microblog.Application
import groovyx.net.http.RESTClient
import org.springframework.boot.SpringApplication

class AppDriver {

    static RESTClient client = new RESTClient('http://localhost:8080')

    static {
        client.handler.failure = client.handler.success
    }

    def service

    def start() {
        service = SpringApplication.run(Application.class)
    }

    def stop() {
        service.close()
    }

    def createNewPost(newPost) {
        def response = client.post(path: '/posts',
                body: newPost,
                contentType: "application/json")
        return response
    }


    def translateStrings(originalTextMap) {
        def response = sendToTranslate(originalTextMap)
        assert response.status == 200
        return response.data
    }

    def translateStringsShouldFail(originalTextMap) {
        def response = sendToTranslate(originalTextMap)
        assert response.status == 412
        return response
    }

    def sendToTranslate(originalTextMap) {
        def response = client.post(path: '/v1/translations/translate',
                body: originalTextMap,
                contentType: "application/json")
        return response
    }

    def getTranslationByStringUUID(originalStringUUID) {
        def response = getResponseTranslationByStringUUID(originalStringUUID)
        assert response.status == 200
        return response.data[0]
    }

    def getTranslationByStringUUIDShouldFailNotFound(originalStringUUID) {
        def response = getResponseTranslationByStringUUID(originalStringUUID)
        assert response.status == 404
        return response
    }

    def getResponseTranslationByStringUUID(originalStringUUID) {
        def response = client.get(path: '/v1/translations',
                query: [uuid: originalStringUUID])
        return response
    }

    def getTranslationByEntityId(entityId) {
        def response = client.get(path: '/v1/translations',
                query: [entityId: entityId, entityType: "moment"])
        assert response.status == 200
        return response.data
    }

    def updateTranslatedLanguage(id, translation, locale) {
        def response = callUpdateTranslation(id, translation, locale)
        assert response.status == 200
        return response.data
    }

    def updateTranslatedLanguageRequest(uid, translation, locale) {
        client.put(path: "/v1/translations/$uid/languages/$locale",
                contentType: "application/json",
                body: translation)
    }

    def addNewTranslation(uid, translation, locale) {
        client.post(path: "/v1/translations/$uid/languages",
                contentType: "application/json",
                body: "{\"newText\" : \"$translation\", \"language\": \"$locale\"}")
    }

    def addEmptyTranslation(uid, locale) {
        client.post(path: "/v1/translations/$uid/languages",
                contentType: "application/json",
                body: "{\"language\": \"$locale\"}")
    }

    def createNewTranslation(text) {
        def response = client.post(path: "/v1/translations",
                contentType: "application/json",
                body: "{\"newText\" : \"$text\"}")
        assert response.status == 201
        return response.data
    }

    def createNewExistingTranslation(text) {
        def response = client.post(path: "/v1/translations",
                contentType: "application/json",
                body: "{\"newText\" : \"$text\"}")
        assert response.status == 200
        return response.data
    }

    def getTranslatedEntity(String entityType, String entityId) {
        def response = client.get(path: "v1/translatedEntities/$entityType/$entityId")
        assert response.status == 200
        response.data
    }

    def getLanguageTranslatedEntity(String entityType, String entityId, String language) {
        def response = client.get(path: "v1/translatedEntities/$entityType/$entityId",
                query: ["language": language])
        assert response.status == 200
        response.data
    }

    def failToUpdateTranslation(id, translation, locale) {
        def response = callUpdateTranslation(id, translation, locale)
        assert response.status == 412
    }

    private callUpdateTranslation(uid, translation, locale) {
        def response = client.put(path: "/v1/translations/$uid/$locale",
                contentType: "application/json",
                body: "{\"newText\" : \"$translation\"}")
        return response
    }

    def failedExportTranslations(exportRequest) {
        def response = exportTranslations(exportRequest)
        return response
    }

    def successfulExportTranslations(exportRequest) {
        def response = exportTranslations(exportRequest)
        assert response.status == 204
        return response
    }

    def exportTranslations(exportRequest) {
        def response = client.post(path: "/v1/translations/export",
                contentType: "application/json",
                body: exportRequest)
        return response
    }


}
