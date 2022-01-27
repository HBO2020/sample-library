import { element, by, ElementFinder } from 'protractor';

export class ImageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-image div table .btn-danger'));
  title = element.all(by.css('jhi-image div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ImageUpdatePage {
  pageTitle = element(by.id('jhi-image-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  imageBookInput = element(by.id('file_imageBook'));
  imageLibelleInput = element(by.id('field_imageLibelle'));
  imageCodeInput = element(by.id('field_imageCode'));

  bookSelect = element(by.id('field_book'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setImageBookInput(imageBook: string): Promise<void> {
    await this.imageBookInput.sendKeys(imageBook);
  }

  async getImageBookInput(): Promise<string> {
    return await this.imageBookInput.getAttribute('value');
  }

  async setImageLibelleInput(imageLibelle: string): Promise<void> {
    await this.imageLibelleInput.sendKeys(imageLibelle);
  }

  async getImageLibelleInput(): Promise<string> {
    return await this.imageLibelleInput.getAttribute('value');
  }

  async setImageCodeInput(imageCode: string): Promise<void> {
    await this.imageCodeInput.sendKeys(imageCode);
  }

  async getImageCodeInput(): Promise<string> {
    return await this.imageCodeInput.getAttribute('value');
  }

  async bookSelectLastOption(): Promise<void> {
    await this.bookSelect.all(by.tagName('option')).last().click();
  }

  async bookSelectOption(option: string): Promise<void> {
    await this.bookSelect.sendKeys(option);
  }

  getBookSelect(): ElementFinder {
    return this.bookSelect;
  }

  async getBookSelectedOption(): Promise<string> {
    return await this.bookSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ImageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-image-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-image'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
