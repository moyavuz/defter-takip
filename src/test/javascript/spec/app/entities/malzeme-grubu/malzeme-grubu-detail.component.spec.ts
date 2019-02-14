/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeGrubuDetailComponent } from 'app/entities/malzeme-grubu/malzeme-grubu-detail.component';
import { MalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

describe('Component Tests', () => {
    describe('MalzemeGrubu Management Detail Component', () => {
        let comp: MalzemeGrubuDetailComponent;
        let fixture: ComponentFixture<MalzemeGrubuDetailComponent>;
        const route = ({ data: of({ malzemeGrubu: new MalzemeGrubu(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeGrubuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MalzemeGrubuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MalzemeGrubuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.malzemeGrubu).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
