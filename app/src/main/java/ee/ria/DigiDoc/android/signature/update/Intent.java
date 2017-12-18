package ee.ria.DigiDoc.android.signature.update;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.io.File;

import ee.ria.DigiDoc.android.document.data.Document;
import ee.ria.DigiDoc.android.signature.data.Signature;
import ee.ria.DigiDoc.android.utils.files.FileStream;
import ee.ria.DigiDoc.android.utils.mvi.MviIntent;

interface Intent extends MviIntent {

    @AutoValue
    abstract class InitialIntent implements Intent {

        abstract File containerFile();

        static InitialIntent create(File containerFile) {
            return new AutoValue_Intent_InitialIntent(containerFile);
        }
    }

    @AutoValue
    abstract class AddDocumentsIntent implements Intent {

        @Nullable abstract File containerFile();

        @Nullable abstract ImmutableList<FileStream> fileStreams();

        static AddDocumentsIntent pick(File containerFile) {
            return new AutoValue_Intent_AddDocumentsIntent(containerFile, null);
        }

        static AddDocumentsIntent add(File containerFile, ImmutableList<FileStream> fileStreams) {
            return new AutoValue_Intent_AddDocumentsIntent(containerFile, fileStreams);
        }

        static AddDocumentsIntent clear() {
            return new AutoValue_Intent_AddDocumentsIntent(null, null);
        }
    }

    @AutoValue
    abstract class OpenDocumentIntent implements Intent {

        @Nullable abstract File containerFile();

        @Nullable abstract Document document();

        static OpenDocumentIntent open(File containerFile, Document document) {
            return new AutoValue_Intent_OpenDocumentIntent(containerFile, document);
        }

        static OpenDocumentIntent clear() {
            return new AutoValue_Intent_OpenDocumentIntent(null, null);
        }
    }

    @AutoValue
    abstract class DocumentsSelectionIntent implements Intent {

        @Nullable abstract ImmutableSet<Document> documents();

        static DocumentsSelectionIntent create(ImmutableSet<Document> documents) {
            return new AutoValue_Intent_DocumentsSelectionIntent(documents);
        }

        static DocumentsSelectionIntent clear() {
            return new AutoValue_Intent_DocumentsSelectionIntent(null);
        }
    }

    @AutoValue
    abstract class RemoveDocumentsIntent implements Intent {

        @Nullable abstract File containerFile();

        @Nullable abstract ImmutableSet<Document> documents();

        static RemoveDocumentsIntent create(File containerFile, ImmutableSet<Document> documents) {
            return new AutoValue_Intent_RemoveDocumentsIntent(containerFile, documents);
        }

        static RemoveDocumentsIntent clear() {
            return new AutoValue_Intent_RemoveDocumentsIntent(null, null);
        }
    }

    @AutoValue
    abstract class SignatureListVisibilityIntent implements Intent {

        abstract boolean isVisible();

        static SignatureListVisibilityIntent create(boolean isVisible) {
            return new AutoValue_Intent_SignatureListVisibilityIntent(isVisible);
        }
    }

    @AutoValue
    abstract class SignatureRemoveSelectionIntent implements Intent {

        @Nullable abstract Signature signature();

        static SignatureRemoveSelectionIntent create(@Nullable Signature signature) {
            return new AutoValue_Intent_SignatureRemoveSelectionIntent(signature);
        }
    }

    @AutoValue
    abstract class SignatureRemoveIntent implements Intent {

        @Nullable abstract File containerFile();

        @Nullable abstract Signature signature();

        static SignatureRemoveIntent create(File containerFile, Signature signature) {
            return new AutoValue_Intent_SignatureRemoveIntent(containerFile, signature);
        }

        static SignatureRemoveIntent clear() {
            return new AutoValue_Intent_SignatureRemoveIntent(null, null);
        }
    }
}