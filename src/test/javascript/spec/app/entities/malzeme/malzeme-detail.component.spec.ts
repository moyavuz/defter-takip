/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeDetailComponent } from 'app/entities/malzeme/malzeme-detail.component';
import { Malzeme } from 'app/shared/model/malzeme.model';

describe('Component Tests', () => {
    describe('Malzeme Management Detail Component', () => {
        let comp: MalzemeDetailComponent;
        let fixture: ComponentFixture<MalzemeDetailComponent>;
        const route = ({ data: of({ malzeme: new Malzeme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MalzemeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MalzemeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.malzeme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
