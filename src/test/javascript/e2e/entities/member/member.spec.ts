import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MemberComponentsPage, MemberDeleteDialog, MemberUpdatePage } from './member.page-object';

const expect = chai.expect;

describe('Member e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let memberComponentsPage: MemberComponentsPage;
  let memberUpdatePage: MemberUpdatePage;
  let memberDeleteDialog: MemberDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Members', async () => {
    await navBarPage.goToEntity('member');
    memberComponentsPage = new MemberComponentsPage();
    await browser.wait(ec.visibilityOf(memberComponentsPage.title), 5000);
    expect(await memberComponentsPage.getTitle()).to.eq('sampleLibraryApp.member.home.title');
    await browser.wait(ec.or(ec.visibilityOf(memberComponentsPage.entities), ec.visibilityOf(memberComponentsPage.noResult)), 1000);
  });

  it('should load create Member page', async () => {
    await memberComponentsPage.clickOnCreateButton();
    memberUpdatePage = new MemberUpdatePage();
    expect(await memberUpdatePage.getPageTitle()).to.eq('sampleLibraryApp.member.home.createOrEditLabel');
    await memberUpdatePage.cancel();
  });

  it('should create and save Members', async () => {
    const nbButtonsBeforeCreate = await memberComponentsPage.countDeleteButtons();

    await memberComponentsPage.clickOnCreateButton();

    await promise.all([
      memberUpdatePage.setIdMemberInput('idMember'),
      memberUpdatePage.setPasswordMemberInput('passwordMember'),
      memberUpdatePage.bookSelectLastOption(),
    ]);

    await memberUpdatePage.save();
    expect(await memberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await memberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Member', async () => {
    const nbButtonsBeforeDelete = await memberComponentsPage.countDeleteButtons();
    await memberComponentsPage.clickOnLastDeleteButton();

    memberDeleteDialog = new MemberDeleteDialog();
    expect(await memberDeleteDialog.getDialogTitle()).to.eq('sampleLibraryApp.member.delete.question');
    await memberDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(memberComponentsPage.title), 5000);

    expect(await memberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
